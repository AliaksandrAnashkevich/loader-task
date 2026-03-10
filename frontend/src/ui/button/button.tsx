import { ReactNode, ButtonHTMLAttributes } from "react";

type ButtonProps = {
  children: ReactNode;
} & ButtonHTMLAttributes<HTMLButtonElement>;

const Button = ({ children, ...props }: ButtonProps) => {
  return (
    <button
      {...props}
      className="px-8 py-2 text-sm font-medium text-white rounded-xl bg-red-500 transition
  hover:bg-red-600 active:scale-[0.98]
  disabled:bg-gray-300 disabled:text-gray-500 disabled:cursor-not-allowed disabled:active:scale-100"
    >
      {children}
    </button>
  );
};

export default Button;
