import { useState } from "react";
import { api } from "../api/axios";
import { Loading } from "../enum/loading";

type SearchParams = {
  number?: string;
  sortBy?: string;
  direction?: "asc" | "desc";
};

export const useForkliftSearch = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [data, setData] = useState(null);

  const searchForklifts = async ({
    number,
    sortBy = "id",
    direction = "asc",
  }: SearchParams) => {
    try {
      setLoading(true);
      setError(null);

      const response = await api.get("/forklift/search", {
        params: {
          number,
          sortBy,
          direction,
        },
      });

      setData(response.data);
    } catch (err: any) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  return {
    searchForklifts,
    data,
    loading,
    error,
  };
};

export const useDeleteForklift = () => {
  const [loading, setLoading] = useState("idle");
  const [error, setError] = useState(null);

  const deleteForklift = async (id: number) => {
    try {
      setLoading(Loading.LOADING);
      setError(null);

      await api.delete(`/forklift/${id}`);
    } catch (err: any) {
      setLoading(Loading.FAILED);
      setError(err);
    } finally {
      setLoading((prev) => (prev === Loading.FAILED ? prev : Loading.SUCCESS));
    }
  };

  return {
    deleteForklift,
    loading,
    error,
  };
};

type ForkliftUpdate = {
  number: string;
};

export const useUpdateForklift = () => {
  const [loading, setLoading] = useState("idle");
  const [error, setError] = useState(null);

  const updateForklift = async (id: number, data: ForkliftUpdate) => {
    try {
      setLoading(Loading.LOADING);
      setError(null);

      const response = await api.patch(`/forklift/${id}`, data);

      return response.data;
    } catch (err: any) {
      setLoading(Loading.FAILED);
      setError(err);
    } finally {
      setLoading((prev) => (prev === Loading.FAILED ? prev : Loading.SUCCESS));
    }
  };

  return {
    updateForklift,
    loading,
    error,
  };
};

type CreateForkliftDto = {
  number: string;
};

export const useCreateForklift = () => {
  const [loading, setLoading] = useState("idle");
  const [error, setError] = useState(null);
  const [data, setData] = useState(null);

  const createForklift = async (payload: CreateForkliftDto) => {
    try {
      setError(null);
      setLoading(Loading.LOADING);

      const response = await api.post("/forklift", payload);

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
    createForklift,
    loading,
    error,
    data,
  };
};
