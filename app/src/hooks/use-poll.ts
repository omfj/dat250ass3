import { useQuery } from "@tanstack/react-query";
import { apiClient } from "../lib/api/client";
import { Poll } from "../lib/api/types";

export const usePoll = (id: string) => {
  const { data: poll, ...rest } = useQuery({
    queryKey: ["poll", id],
    queryFn: () => apiClient.get(`polls/${id}`).json<Poll | null>(),
  });

  return {
    poll,
    ...rest,
  };
};
