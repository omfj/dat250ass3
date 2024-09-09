import { useHealth } from "../hooks/use-health";

export const Root = () => {
  const health = useHealth();

  return (
    <main className="flex flex-col items-center justify-center h-screen">
      <h1 className="text-2xl font-medium">Hello world</h1>
      <p className="text-lg">
        This is a simple poll application. Status: {health}.
      </p>
    </main>
  );
};
