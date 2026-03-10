import { useState } from "react";
import { api } from "../api/axios";

type LoginData = {
  login: string;
  password: string;
};

export const useLogin = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const login = async (data: LoginData) => {
    try {
      setLoading(true);
      setError(null);

      const response = await api.post("/login", data);

      return response.data;
    } catch (err: any) {
      setError(err.response?.data?.message || "Login error");
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return {
    login,
    loading,
    error,
  };
};
