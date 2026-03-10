import { FieldError, UseFormRegisterReturn } from "react-hook-form";

type InputProps = {
  label: string;
  type?: string;
  registration: UseFormRegisterReturn;
  error?: FieldError;
};

const Input = ({ label, type = "text", registration, error }: InputProps) => {
  return (
    <div className="w-full">
      <div className="relative">
        <input
          type={type}
          {...registration}
          placeholder=" "
          step="1"
          className={`peer w-full rounded-xl border bg-white px-4 pt-4 pb-1 text-sm outline-none transition
          ${
            error
              ? "border-red-500 focus:border-red-500"
              : "border-gray-300 focus:border-green-500"
          }`}
        />

        <label
          className={`absolute left-4 top-1 text-sm transition-all
          peer-placeholder-shown:top-2
          peer-placeholder-shown:text-gray-400
          peer-placeholder-shown:text-base
          peer-focus:top-1
          peer-focus:text-sm
          pointer-events-none
          peer-focus:text-green-600
          ${error ? "text-red-500" : "text-gray-500"}
        `}
        >
          {label}
        </label>
      </div>

      {error && <p className="mt-1 text-xs text-red-500">{error.message}</p>}
    </div>
  );
};

export default Input;
