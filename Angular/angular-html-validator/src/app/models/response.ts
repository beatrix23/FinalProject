import {Message} from './message';
export interface ValidationResponse {
    location: string;
    messages: Message[];
}