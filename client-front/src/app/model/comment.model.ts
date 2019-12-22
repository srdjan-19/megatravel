export class Comment {
    content: string;
    postedBy: string;
    visible: boolean;
}

export class CreateCommentRequest {
    accommodationName: string;
    content: string;
    fromDate: Date;
    tillDate: Date;
}

export class CreateCommentResponse  {
    feedback: string;
}

