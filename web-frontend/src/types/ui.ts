export type StatusTone = "active" | "inactive" | "pending" | "failed" | "success";

export interface Candidate {
  id: number;
  name: string;
  email: string;
  role: string;
  stage: string;
  status: StatusTone;
  updatedAt: string;
}

export interface TableColumn<T> {
  key: keyof T | string;
  label: string;
  width?: string;
  hidden?: boolean;
}
