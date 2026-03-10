import { useState } from "react";
import { Loading } from "../enum/loading";
import { api } from "../api/axios";

export const useDeleteForkliftTimeout = () => {
  const [loading, setLoading] = useState("idle");
  const [error, setError] = useState(null);

  const deleteForkliftTimeout = async (id: number) => {
    try {
      setLoading(Loading.LOADING);
      setError(null);

      await api.delete(`/timeout/${id}`);
    } catch (err: any) {
      setLoading(Loading.FAILED);
      setError(err);
    } finally {
      setLoading((prev) => (prev === Loading.FAILED ? prev : Loading.SUCCESS));
    }
  };

  return {
    deleteForkliftTimeout,
    loading,
    error,
  };
};

type ForkliftTimeoutUpdate = {
  number: string;
};

export const useUpdateForkliftTimeout = () => {
  const [loading, setLoading] = useState("idle");
  const [error, setError] = useState(null);

  const updateForkliftTimeout = async (
    id: number,
    data: ForkliftTimeoutUpdate,
  ) => {
    try {
      setLoading(Loading.LOADING);
      setError(null);

      const response = await api.patch(`/timeout/${id}`, data);

      return response.data;
    } catch (err: any) {
      setLoading(Loading.FAILED);
      setError(err);
    } finally {
      setLoading((prev) => (prev === Loading.FAILED ? prev : Loading.SUCCESS));
    }
  };

  return {
    updateForkliftTimeout,
    loading,
    error,
  };
};

type CreateForkliftTimeoutDto = {
  number: string;
};

export const useCreateForkliftTimeout = () => {
  const [loading, setLoading] = useState("idle");
  const [error, setError] = useState(null);
  const [data, setData] = useState(null);

  const createForkliftTimeout = async (payload: CreateForkliftTimeoutDto) => {
    try {
      setError(null);
      setLoading(Loading.LOADING);

      const response = await api.post("/timeout", payload);

      setData(response.data);

      return response.data;
    } catch (err: any) {
      setLoading(Loading.FAILED);
      setError(err);
      throw err;
    } finally {
      setLoading((prev) => (prev === Loading.FAILED ? prev : Loading.SUCCESS));
    }
  };

  return {
    createForkliftTimeout,
    loading,
    error,
    data,
  };
};

export const useGetByIdForkliftTimeout = () => {
  const [loading, setLoading] = useState("idle");
  const [error, setError] = useState(null);
  const [data, setData] = useState(null);

  const getByIdForkliftTimeout = async ({
    id,
    sortBy = "detectedDate",
    direction = "desc",
  }: any) => {
    try {
      setError(null);
      setLoading(Loading.LOADING);

      const response = await api.get(`/timeout/forklift/${id}`, {
        params: {
          sortBy,
          direction,
        },
      });

      setData(response.data);

      return response.data;
    } catch (err: any) {
      setLoading(Loading.FAILED);
      setError(err);
      throw err;
    } finally {
      setLoading((prev) => (prev === Loading.FAILED ? prev : Loading.SUCCESS));
    }
  };

  return {
    getByIdForkliftTimeout,
    loading,
    error,
    data,
  };
};
