import { cn } from "../lib/cn";

export type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement>;

export const Button = ({ className, ...props }: ButtonProps) => {
  return (
    <button
      className={cn(
        "rounded-lg bg-blue-500 hover:bg-blue-600 transition-all px-4 py-2 text-white",
        className
      )}
      {...props}
    />
  );
};
