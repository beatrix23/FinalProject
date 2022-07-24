import {Message} from './message';
export interface ValidationResponse {
    href: string;
    messages: Message[];
}