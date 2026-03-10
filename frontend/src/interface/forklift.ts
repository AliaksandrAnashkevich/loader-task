export type ForkliftInterface = {
  id: number;
  brand: string;
  number: string;
  loadCapacity: string;
  isActive: boolean;
  updateDate: string;
  fio: string;
};
export type ForkliftCreateInterface = Omit<
  ForkliftInterface,
  "id" | "isActive" | "updateDate" | "fio"
>;

export type ForkliftUpdateInterface = Omit<
  ForkliftInterface,
  "isActive" | "updateDate" | "fio"
>;

export type SearchFormData = {
  forkliftNumber: string;
};
