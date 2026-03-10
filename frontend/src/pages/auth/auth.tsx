import { useForm } from "react-hook-form";
import Input from "../../ui/input/input";
import Button from "../../ui/button/button";
import { useLogin } from "../../hooks/auth";
import { Dispatch, SetStateAction } from "react";

type AuthData = {
  login: string;
  password: string;
};

type AuthProps = {
  setIsAuth: Dispatch<SetStateAction<boolean>>;
};

const Auth = ({ setIsAuth }: AuthProps) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<AuthData>();

  const { login, error } = useLogin();

  const onSubmit = async (data: AuthData) => {
    try {
      const res = await login(data);

      localStorage.setItem("token", res.access_token);

      setIsAuth(true);
    } catch {
      setIsAuth(false);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="w-full max-w-sm space-y-5 rounded-2xl border border-gray-200 bg-white p-6 shadow-sm"
      >
        <h2 className="text-center text-xl font-semibold text-gray-700">
          Авторизация
        </h2>

        <Input
          label="Логин"
          registration={register("login", {
            required: "Введите логин",
          })}
          error={errors.login}
        />

        <Input
          label="Пароль"
          type="password"
          registration={register("password", {
            required: "Введите пароль",
          })}
          error={errors.password}
        />

        {error && (
          <div className="rounded-lg border border-red-300 bg-red-50 px-4 py-3 text-sm text-red-700">
            Пользователь с таким логином не найден. Проверьте логин и пароль.
          </div>
        )}
        <Button type={"submit"}>Войти</Button>
      </form>
    </div>
  );
};

export default Auth;
