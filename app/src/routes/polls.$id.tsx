import { useParams } from "react-router-dom";
import invariant from "tiny-invariant";
import { usePoll } from "../hooks/use-poll";
import { VoteOptionButton } from "../components/vote-option-button";
import { useAuth } from "../hooks/use-auth";

export const Poll = () => {
  const { id } = useParams();
  const { user } = useAuth();
  invariant(id, "id is required");
  const { poll, isLoading } = usePoll(id);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!poll) {
    return <div>404...</div>;
  }

  const userVotes = poll.votes
    .filter((vote) => vote.userId === user?.id)
    .map((vote) => vote.id);

  return (
    <main className="p-4 space-y-8 max-w-xl">
      <h1 className="text-2xl font-medium">Poll {poll.question}</h1>

      <ul className="text-sm text-gray-500">
        <li>Expires at: {new Date(poll.expiresAt * 1000).toLocaleString()}</li>
      </ul>

      <ol className="space-y-4">
        {poll.options.map((option) => {
          const numVotes = poll.votes.filter(
            (vote) => vote.id === option.id
          ).length;

          return (
            <li key={option.id}>
              <VoteOptionButton
                pollId={id}
                optionId={option.id}
                hasVoted={userVotes.includes(option.id)}
              >
                {option.order + 1}. {option.caption} ({numVotes})
              </VoteOptionButton>
            </li>
          );
        })}
      </ol>
    </main>
  );
};
