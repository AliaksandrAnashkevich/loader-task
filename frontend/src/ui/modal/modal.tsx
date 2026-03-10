import { useEffect } from "react";
import { useForm } from "react-hook-form";
import Input from "../input/input";
import Button from "../button/button";
import input from "../input/input";

type Field = {
  name: string;
  label: string;
  type?: string;
  placeholder?: string;
};

type FormModalProps = {
  open: boolean;
  title: string;
  fields: Field[];
  initialValues?: Record<string, any>;
  onClose: () => void;
  onSubmit: (data: any) => void;
};

const FormModal = ({
  open,
  title,
  fields,
  initialValues,
  onClose,
  onSubmit,
}: FormModalProps) => {
  const { register, handleSubmit, resetField, reset } = useForm();

  useEffect(() => {
    if (initialValues) {
      reset(initialValues);
    } else {
      fields.forEach((item) => {
        resetField(item.name, { defaultValue: "" });
      });
      reset();
    }
  }, [initialValues, title]);

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40">
      <div className="w-full max-w-md rounded-2xl bg-white p-6 shadow-xl animate-in fade-in zoom-in">
        <div className="mb-4 flex items-center justify-between">
          <h2 className="text-lg font-semibold">{title}</h2>

          <button
            onClick={onClose}
            className="text-gray-400 hover:text-gray-600"
          >
            ✕
          </button>
        </div>

        <form
          onSubmit={(e) => {
            handleSubmit(onSubmit)(e);
            reset();
          }}
          className="space-y-4"
        >
          {fields.map((field) => {
            if (field.type !== "checkbox") {
              return (
                <Input
                  label={field.label}
                  type={field.type || "text"}
                  registration={register(field.name)}
                />
              );
            } else {
              return (
                <div className="flex items-center gap-2">
                  <input
                    id={field.name}
                    {...register(field.name)}
                    type="checkbox"
                  />
                  <label htmlFor={field.name}>Активный</label>
                </div>
              );
            }
          })}

          <div className="flex justify-end gap-3 pt-3">
            <button
              type="button"
              onClick={onClose}
              className="rounded-lg border px-4 py-2 hover:bg-gray-100"
            >
              Отмена
            </button>

            <Button>Сохранить</Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default FormModal;
