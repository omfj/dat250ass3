import { useMutation } from "@tanstack/react-query";
import { AddPollInput } from "../lib/api/types";
import { apiClient } from "../lib/api/client";

export const usePollMutation = () => {
  const { mutateAsync: addPoll, ...rest } = useMutation({
    mutationFn: (input: AddPollInput) => {
      return apiClient.post("polls", {
        json: input,
      });
    },
  });

  return {
    addPoll,
    ...rest,
  };
};
