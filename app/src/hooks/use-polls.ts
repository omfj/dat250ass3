import { useQuery } from "@tanstack/react-query";
import { apiClient } from "../lib/api/client";
import { Poll } from "../lib/api/types";

export const usePolls = () => {
  const { data: polls, ...rest } = useQuery({
    queryKey: ["polls"],
    queryFn: () => apiClient.get("polls").json<Array<Poll>>(),
  });

  return {
    polls,
    ...rest,
  };
};
