import ky from "ky";
import { API_URL } from "../constants";

export const apiClient = ky.extend({
  prefixUrl: API_URL,
  credentials: "include",
});
