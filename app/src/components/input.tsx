import { cn } from "../lib/cn";

type InputProps = React.InputHTMLAttributes<HTMLInputElement>;

export const Input = ({ className, ...props }: InputProps) => {
  return (
    <input
      className={cn(
        "block w-full rounded-md h-10 border-gray-300 p-2 text-sm flex-1",
        className
      )}
      {...props}
    />
  );
};
