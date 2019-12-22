export class Message {
    recipient: string;
    sender: string;
    sentBy: string;
    content: string;
}

export class CreateMessageRequest {
    sender: string;
    recipient: string;
    content: string;
}

export class CreateMessageResponse {
    feedback: string;
}