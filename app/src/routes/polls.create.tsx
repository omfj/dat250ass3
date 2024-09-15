import { useState } from "react";
import { X, ArrowUp, ArrowDown } from "lucide-react";
import { useArrayState } from "../hooks/use-array-state";
import { usePollMutation } from "../hooks/use-poll-mutation";
import { Input } from "../components/input";
import { Label } from "../components/label";
import { Button } from "../components/button";

export const PollCreate = () => {
  const { addPoll } = usePollMutation();
  const [question, setQuestion] = useState("");
  const [expiresAt, setExpiresAt] = useState(() => new Date().getTime() / 1000);
  const [options, { add, update, remove, moveBack, moveForward }] =
    useArrayState<string>([""]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    await addPoll({
      question,
      expiresAt,
      options: options.map((option, i) => ({ caption: option, order: i })),
    });

    setQuestion("");
    setExpiresAt(new Date().getTime());
    options.forEach((_, i) => remove(i));
  };

  return (
    <main className="p-4 space-y-8">
      <h1 className="text-2xl font-medium">Create poll</h1>

      <form onSubmit={handleSubmit} className="space-y-4 max-w-xl">
        <div className="space-y-1">
          <Label htmlFor="question">Question</Label>
          <Input
            id="question"
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
          />
        </div>

        <div className="space-y-1">
          <Label htmlFor="expiresAt">Expires at</Label>
          <Input
            id="expiresAt"
            type="datetime-local"
            value={new Date(expiresAt * 1000).toISOString().slice(0, 16)}
            onChange={(e) =>
              setExpiresAt(new Date(e.target.value).getTime() / 1000)
            }
          />
        </div>

        <div className="space-y-1">
          <Label htmlFor="options">Options</Label>
          <div className="rounded-xl border border-gray-300 space-y-2 p-4">
            {options.map((option, i) => {
              return (
                <div key={i} className="flex items-center gap-2">
                  <Input
                    type="text"
                    placeholder={`Option ${i + 1}...`}
                    value={option}
                    onChange={(e) => update(i, e.target.value)}
                  />

                  <button
                    type="button"
                    className="h-10 w-10 hover:bg-gray-100 flex items-center justify-center rounded-xl text-gray-500 disabled:opacity-50 disabled:cursor-not-allowed"
                    onClick={() => moveBack(i)}
                    disabled={i <= 0}
                  >
                    <ArrowUp className="w-4 h-4 text-gray-500" />
                  </button>

                  <button
                    type="button"
                    className="h-10 w-10 hover:bg-gray-100 flex items-center justify-center rounded-xl text-gray-500 disabled:opacity-50 disabled:cursor-not-allowed"
                    onClick={() => moveForward(i)}
                    disabled={i >= options.length - 1}
                  >
                    <ArrowDown className="w-4 h-4 text-gray-500" />
                  </button>

                  <button
                    type="button"
                    className="h-10 w-10 hover:bg-gray-100 flex items-center justify-center rounded-xl text-gray-500 disabled:opacity-50 disabled:cursor-not-allowed"
                    onClick={() => remove(i)}
                    disabled={options.length <= 1}
                  >
                    <X className="w-4 h-4 text-gray-500" />
                  </button>
                </div>
              );
            })}

            <Button className="w-full" type="button" onClick={() => add("")}>
              Add option
            </Button>
          </div>
        </div>

        <Button className="w-full">Add poll</Button>
      </form>
    </main>
  );
};
