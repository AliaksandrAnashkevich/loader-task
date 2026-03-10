import { AgGridReact } from "ag-grid-react";
import { useEffect, useMemo, useState } from "react";
import Button from "../../ui/button/button";
import Search from "../../components/search/search";
import {
  useCreateForklift,
  useDeleteForklift,
  useForkliftSearch,
  useUpdateForklift,
} from "../../hooks/forklift";
import FormModal from "../../ui/modal/modal";
import { Loading } from "../../enum/loading";
import { useForm } from "react-hook-form";
import ConfirmModal from "../../ui/modal/confirmModal";
import ForkliftTimeout from "../../components/forkliftTimeout/forkliftTimeout";
import {
  ForkliftCreateInterface,
  ForkliftInterface,
  ForkliftUpdateInterface,
  SearchFormData,
} from "../../interface/forklift";
import { ColDef, SelectionChangedEvent } from "ag-grid-community";

const Main = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isConfirmModalOpen, setIsConfirmModalOpen] = useState(false);
  const [deleteId, setDeleteId] = useState<number | null>(null);
  const [modalTitle, setModalTitle] = useState("");
  const [modalInitialValues, setModalInitialValues] =
    useState<ForkliftUpdateInterface>();
  const [modalType, setModalType] = useState("");
  const [sort, setSort] = useState({ sortBy: "id", direction: "asc" });

  const [selectedRowForkLift, setSelectedRowForkLift] =
    useState<ForkliftInterface | null>(null);

  const {
    register,
    handleSubmit,
    reset,
    getValues,
    formState: { errors },
  } = useForm<SearchFormData>();

  const { data, searchForklifts } = useForkliftSearch();
  const { createForklift, loading: createLoading } = useCreateForklift();
  const { updateForklift, loading: updateLoading } = useUpdateForklift();
  const { deleteForklift, loading: deleteLoading } = useDeleteForklift();

  const ActionsRenderer = (props: { data: ForkliftInterface }) => {
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

  const columnDefs = useMemo<ColDef<ForkliftInterface>[]>(
    () => [
      { field: "id", headerName: "Код записи", sortable: true },
      { field: "brand", headerName: "Марка", sortable: true },
      { field: "number", headerName: "Номер", sortable: true },
      { field: "loadCapacity", headerName: "Грузоподъемность", sortable: true },
      { field: "isActive", headerName: "Активен", sortable: true },
      {
        field: "updateDate",
        headerName: "Время и дата изменения",
        sortable: true,
      },
      { field: "fio", headerName: "Пользователь", sortable: true },
      {
        headerName: "Действия",
        cellRenderer: ActionsRenderer,
        width: 160,
      },
    ],
    [],
  );

  const handleDelete = async () => {
    if (!deleteId) return;

    await deleteForklift(deleteId);
    setDeleteId(null);
  };

  const getForkLifts = async (data = {}) => {
    await searchForklifts({ ...data });
  };

  const onSubmitFormModal = async (data: ForkliftCreateInterface) => {
    if (modalType === "add") {
      await createForklift(data);
    } else if (modalType === "edit" && modalInitialValues) {
      await updateForklift(modalInitialValues.id, data);
    }
  };

  const handleSelectedRow = (
    event: SelectionChangedEvent<ForkliftInterface>,
  ) => {
    const selectedRows = event.api.getSelectedRows();

    setSelectedRowForkLift(selectedRows[0]);
  };

  const logout = () => {
    localStorage.removeItem("token");
    window.location.reload();
  };

  const onSortChanged = (params: any) => {
    const columnState = params.api.getColumnState();

    const sortModel = columnState.find((col: any) => col.sort);

    if (!sortModel || sortModel.length === 0) {
      getForkLifts({
        number: getValues("forkliftNumber"),
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

    getForkLifts({
      number: getValues("forkliftNumber"),
      sortBy: colId,
      direction: sort,
    });
  };

  useEffect(() => {
    if (localStorage.getItem("token")) {
      getForkLifts();
    }
  }, []);

  useEffect(() => {
    if (
      createLoading === Loading.SUCCESS ||
      updateLoading === Loading.SUCCESS
    ) {
      setIsModalOpen(false);
      getForkLifts({ number: getValues("forkliftNumber"), ...sort });
    }
  }, [createLoading, updateLoading]);

  useEffect(() => {
    if (deleteLoading === Loading.SUCCESS) {
      setIsConfirmModalOpen(false);
      getForkLifts({ number: getValues("forkliftNumber"), ...sort });
    }
  }, [deleteLoading]);

  return (
    <>
      <div className={"h-[200px] p-6"}>
        <div className={"flex justify-between"}>
          <h2 className={"text-2xl font-bold mb-4"}>Справочник погрузчиков</h2>

          <Button onClick={logout}>Выйти</Button>
        </div>

        <Search
          searchForklifts={searchForklifts}
          formSearch={{ register, handleSubmit, reset, errors }}
        />

        <div>
          <div className={"mb-4"}>
            <Button
              onClick={() => {
                setIsModalOpen(true);
                setModalTitle("Добавить погрузчика");
                setModalInitialValues(undefined);
                setModalType("add");
              }}
            >
              Добавить
            </Button>
          </div>

          <div className={"flex gap-6"}>
            <div className={"h-[400px] flex-1"}>
              <AgGridReact<ForkliftInterface>
                rowData={data || []}
                columnDefs={columnDefs}
                defaultColDef={{ flex: 1 }}
                rowSelection="single"
                onSelectionChanged={handleSelectedRow}
                onSortChanged={onSortChanged}
              />
            </div>

            <ForkliftTimeout selectedRowForkLift={selectedRowForkLift} />
          </div>
        </div>
      </div>

      <FormModal
        open={isModalOpen}
        fields={[
          {
            name: "brand",
            label: "Марка",
            type: "text",
            placeholder: "Введите марку",
          },
          {
            name: "number",
            label: "Номер",
            type: "text",
            placeholder: "Введите номер",
          },
          {
            name: "loadCapacity",
            label: "Грузоподъемность",
            type: "float",
            placeholder: "Введите грузоподъемность",
          },
        ]}
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

export default Main;
