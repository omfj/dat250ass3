import { ButtonHTMLAttributes } from "react";
import { cn } from "../lib/cn";
import { useVoteMutation } from "../hooks/use-vote-mutation";

type VoteOptionButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  pollId: string;
  optionId: string;
  hasVoted: boolean;
};

export const VoteOptionButton = ({
  hasVoted,
  className,
  pollId,
  optionId,
  children,
  ...props
}: VoteOptionButtonProps) => {
  const [{ addVote }, { deleteVote }] = useVoteMutation(pollId);

  const handleClick = async () => {
    if (hasVoted) {
      await deleteVote({
        optionId,
      });
    } else {
      await addVote({
        optionId,
      });
    }
  };

  return (
    <button
      className={cn(
        "w-full rounded-md bg-blue-500 px-4 py-2 text-white border border-transparent hover:bg-blue-600 transition-colors",
        {
          "border border-blue-600 bg-transparent text-blue-600 hover:bg-blue-100":
            hasVoted,
        },
        className
      )}
      onClick={handleClick}
      {...props}
    >
      {children}
    </button>
  );
};
