import { AgGridReact } from "ag-grid-react";
import { useEffect, useMemo, useState } from "react";
import FormModal from "../../ui/modal/modal";
import ConfirmModal from "../../ui/modal/confirmModal";
import {
  useCreateForkliftTimeout,
  useDeleteForkliftTimeout,
  useGetByIdForkliftTimeout,
  useUpdateForkliftTimeout,
} from "../../hooks/forkliftTimeout";
import Button from "../../ui/button/button";
import { Loading } from "../../enum/loading";
import { ColDef } from "ag-grid-community";
import { ForkliftTimeoutInterface } from "../../interface/forkliftTimeoutInterface";
import { forkliftTimeoutFields } from "./forkliftTimeoutData";

const ForkliftTimeout = ({ selectedRowForkLift }: any) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isConfirmModalOpen, setIsConfirmModalOpen] = useState(false);
  const [deleteId, setDeleteId] = useState<number | null>(null);
  const [modalTitle, setModalTitle] = useState("");
  const [modalInitialValues, setModalInitialValues] = useState<any>();
  const [modalType, setModalType] = useState("");
  const [sort, setSort] = useState({
    sortBy: "detectedDate",
    direction: "desc",
  });

  const { deleteForkliftTimeout, loading: deleteLoading } =
    useDeleteForkliftTimeout();
  const { updateForkliftTimeout, loading: updateLoading } =
    useUpdateForkliftTimeout();
  const { createForkliftTimeout, loading: createLoading } =
    useCreateForkliftTimeout();
  const { getByIdForkliftTimeout, data } = useGetByIdForkliftTimeout();

  const ActionsRenderer = (props: { data: ForkliftTimeoutInterface }) => {
    const handleEdit = () => {
      setIsModalOpen(true);
      setModalTitle("Отредактировать погрузчика");
      setModalInitialValues(props.data);
      setModalType("edit");
    };

    const handleDelete = async () => {
      setIsConfirmModalOpen(true);
      setDeleteId(props.data.id);
    };

    return (
      <div className="flex gap-2">
        <button onClick={handleEdit} className="px-2 w-8">
          ✏️
        </button>

        <button onClick={handleDelete} className="px-2 w-8">
          🗑️
        </button>
      </div>
    );
  };

  const columnDefs = useMemo<ColDef<ForkliftTimeoutInterface>[]>(
    () => [
      { field: "id", headerName: "Код записи", sortable: true },
      { field: "detectedDate", headerName: "Начало", sortable: true },
      { field: "solutionDate", headerName: "Окончание", sortable: true },
      { field: "timeout", headerName: "Время простоя", sortable: false },
      { field: "description", headerName: "Причина", sortable: true },
      {
        headerName: "Действия",
        cellRenderer: ActionsRenderer,
        width: 160,
      },
    ],
    [],
  );

  const onSubmitFormModal = async (data: any) => {
    if (modalType === "add") {
      await createForkliftTimeout({
        ...data,
        forkliftId: selectedRowForkLift.id,
        detectedDate: data.detectedDate.replace("T", " "),
        solutionDate: data.solutionDate.replace("T", " "),
      });
    } else if (modalType === "edit" && modalInitialValues) {
      await updateForkliftTimeout(modalInitialValues.id, {
        ...data,
        detectedDate: data.detectedDate.replace("T", " "),
        solutionDate: data.solutionDate.replace("T", " "),
      });
    }
  };

  const handleDelete = async () => {
    if (!deleteId) return;

    await deleteForkliftTimeout(deleteId);
    setDeleteId(null);
  };

  const onSortChanged = (params: any) => {
    const columnState = params.api.getColumnState();

    const sortModel = columnState.find((col: any) => col.sort);

    if (!sortModel || sortModel.length === 0) {
      getByIdForkliftTimeout({
        id: selectedRowForkLift.id,
        sortBy: "id",
        direction: "asc",
      });

      setSort({ sortBy: "id", direction: "asc" });
      return;
    }

    const { colId, sort } = sortModel;

    setSort({
      sortBy: colId,
      direction: sort,
    });

    getByIdForkliftTimeout({
      id: selectedRowForkLift.id,
      sortBy: colId,
      direction: sort,
    });
  };

  useEffect(() => {
    if (!selectedRowForkLift) return;

    getByIdForkliftTimeout({ id: selectedRowForkLift.id, ...sort });
  }, [selectedRowForkLift]);

  useEffect(() => {
    if (
      createLoading === Loading.SUCCESS ||
      updateLoading === Loading.SUCCESS
    ) {
      setIsModalOpen(false);
      getByIdForkliftTimeout({ id: selectedRowForkLift.id, ...sort });
    }
  }, [createLoading, updateLoading]);

  useEffect(() => {
    if (deleteLoading === Loading.SUCCESS) {
      setIsConfirmModalOpen(false);
      getByIdForkliftTimeout({ id: selectedRowForkLift.id, ...sort });
    }
  }, [deleteLoading]);

  return (
    <>
      <div className={"flex-1"}>
        <div className={"mb-2"}>
          <Button
            onClick={() => {
              setIsModalOpen(true);
              setModalTitle("Добавить погрузчика");
              setModalInitialValues(undefined);
              setModalType("add");
            }}
            disabled={!selectedRowForkLift}
          >
            Добавить
          </Button>
        </div>

        <p className={"font-semibold text-lg mb-2"}>Простой по погрузчику</p>

        <div className={"h-[320px]"}>
          <AgGridReact
            rowData={data || []}
            columnDefs={columnDefs}
            defaultColDef={{ flex: 1 }}
            onSortChanged={onSortChanged}
          />
        </div>
      </div>

      <FormModal
        open={isModalOpen}
        fields={forkliftTimeoutFields}
        onClose={() => setIsModalOpen(false)}
        onSubmit={onSubmitFormModal}
        title={modalTitle}
        initialValues={modalInitialValues}
      />

      <ConfirmModal
        isOpen={isConfirmModalOpen}
        title="Удаление записи"
        description="Вы уверены, что хотите удалить запись?"
        onConfirm={handleDelete}
        onClose={() => {
          setIsConfirmModalOpen(false);
          setDeleteId(null);
        }}
      />
    </>
  );
};

export default ForkliftTimeout;
