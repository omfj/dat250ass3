import { Link } from "react-router-dom";
import { usePolls } from "../hooks/use-polls";

export const Root = () => {
  const { polls } = usePolls();

  return (
    <main className="p-4 space-y-4">
      <h1 className="text-2xl font-medium">Select an active poll</h1>

      <ul className="list-disc pl-8 space-y-2">
        {polls?.map((poll) => {
          return (
            <li key={poll.id}>
              <Link className="hover:underline" to={`/polls/${poll.id}`}>
                <h2>{poll.question}</h2>
              </Link>
            </li>
          );
        })}
      </ul>
    </main>
  );
};
