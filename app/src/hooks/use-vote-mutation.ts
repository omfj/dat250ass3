import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiClient } from "../lib/api/client";
import { AddVoteInput } from "../lib/api/types";

export const useVoteMutation = (pollId: string) => {
  const queryClient = useQueryClient();

  const { mutateAsync: addVote, ...addVoteRest } = useMutation({
    mutationFn: (input: AddVoteInput) =>
      apiClient.post(`polls/${pollId}/vote`, {
        json: {
          optionId: input.optionId,
        },
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["poll", pollId],
      });
    },
  });

  const { mutateAsync: deleteVote, ...deleteVoteRest } = useMutation({
    mutationFn: (input: AddVoteInput) =>
      apiClient.delete(`polls/${pollId}/vote`, {
        json: {
          optionId: input.optionId,
        },
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["poll", pollId],
      });
    },
  });

  return [
    {
      addVote,
      ...addVoteRest,
    },
    {
      deleteVote,
      ...deleteVoteRest,
    },
  ] as const;
};
