import { useQuery } from "@tanstack/react-query";
import { apiClient } from "../lib/api/client";

export const useHealth = (): "health" | "loading" | "error" => {
  const { data, isLoading } = useQuery({
    queryKey: ["health"],
    queryFn: () => apiClient.get("health").text(),
  });

  if (isLoading) {
    return "loading";
  }

  if (data === "OK") {
    return "health";
  }

  return "error";
};
