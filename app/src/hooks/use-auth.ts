import { useQuery, useQueryClient } from "@tanstack/react-query";
import { apiClient } from "../lib/api/client";
import { User } from "../lib/api/types";

export const useAuth = () => {
  const queryClient = useQueryClient();

  const { data: user, ...rest } = useQuery({
    queryKey: ["user"],
    queryFn: () => apiClient.get("whoami").json<User | null>(),
  });

  const logout = async () => {
    await apiClient.post("logout").text();
    await queryClient.invalidateQueries();
  };

  return {
    user,
    logout,
    ...rest,
  };
};
