export type User = {
  id: string;
  name: string;
  email: string;
};

export type VoteOption = {
  id: string;
  caption: string;
  order: number;
};

export type AddVoteInput = {
  optionId: string;
};

export type Vote = {
  id: string;
  userId: string;
};

export type Poll = {
  id: string;
  question: string;
  ownerId: string;
  publishedAt: number;
  expiresAt: number;
  options: Array<VoteOption>;
  votes: Array<Vote>;
};

export type VoteOptionInput = {
  caption: string;
  order: number;
};

export type AddPollInput = {
  question: string;
  expiresAt: number;
  options: Array<VoteOptionInput>;
};
