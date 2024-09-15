import { useState } from "react";

export const useArrayState = <T>(initialValue: Array<T>) => {
  const [value, setValue] = useState(initialValue);

  const add = (item: T) => {
    setValue((prev) => [...prev, item]);
  };

  const remove = (index: number) => {
    setValue((prev) => prev.filter((_, i) => i !== index));
  };

  const update = (index: number, item: T) => {
    setValue((prev) => {
      const newValue = [...prev];
      newValue[index] = item;
      return newValue;
    });
  };

  const moveBack = (index: number) => {
    if (index <= 0) return;
    setValue((prev) => {
      const newValue = [...prev];
      const [removed] = newValue.splice(index, 1);
      newValue.splice(index - 1, 0, removed);
      return newValue;
    });
  };

  const moveForward = (index: number) => {
    if (index >= value.length - 1) return;
    setValue((prev) => {
      const newValue = [...prev];
      const [removed] = newValue.splice(index, 1);
      newValue.splice(index + 1, 0, removed);
      return newValue;
    });
  };

  return [
    value,
    {
      add,
      remove,
      update,
      moveBack,
      moveForward,
    },
  ] as const;
};
