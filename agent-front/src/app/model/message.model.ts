export class Message {
    sender: string;
    recipient: string;
    content: string;
    sentBy: string;
}

export class CreateMessageRequest {
    recipient: string;
    content: string;
}

export class CreateMessageResponse {
    feedback: string;
}