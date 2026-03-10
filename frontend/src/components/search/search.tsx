import Input from "../../ui/input/input";
import Button from "../../ui/button/button";
import {
  FieldError,
  FieldErrors,
  FieldValues,
  Path,
  UseFormHandleSubmit,
  UseFormRegister,
  UseFormReset,
} from "react-hook-form";

type SearchProps<T extends FieldValues> = {
  searchForklifts: ({}) => void;
  formSearch: {
    reset: UseFormReset<T>;
    handleSubmit: UseFormHandleSubmit<T>;
    register: UseFormRegister<T>;
    errors: FieldErrors<T>;
  };
};

export const Search = <T extends FieldValues>({
  searchForklifts,
  formSearch,
}: SearchProps<T>) => {
  const onSubmit = async (data: FieldValues) => {
    await searchForklifts({ number: data.forkliftNumber });
  };

  const handleClear = async () => {
    await searchForklifts({});
    formSearch.reset();
  };

  return (
    <form
      onSubmit={formSearch.handleSubmit(onSubmit)}
      className="flex w-[600px] items-center gap-4 mb-8"
    >
      <h2 className="text-sm font-semibold text-gray-700 whitespace-nowrap">
        Поиск погрузчика
      </h2>

      <Input
        label="Номер погрузчика"
        registration={formSearch.register("forkliftNumber" as Path<T>, {
          required: "Введите номер погрузчика",
        })}
        error={formSearch.errors.forkliftNumber as FieldError | undefined}
      />

      <Button>Найти</Button>

      <button
        type="button"
        onClick={handleClear}
        className="flex-1 rounded-xl border border-gray-300 py-2 px-2 text-sm font-medium text-gray-700 transition hover:bg-gray-100 active:scale-[0.98]"
      >
        Очистить
      </button>
    </form>
  );
};

export default Search;
