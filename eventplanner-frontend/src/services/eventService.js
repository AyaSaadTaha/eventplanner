import api from './api';

export const getEvents = () => api.get('/events/getAllEvents');
export const getEventById = (id) => api.get(`/events/getEventById/${id}`);
export const createEvent = (eventData) => api.post('/events', eventData);
export const updateEvent = (id, eventData) => api.put(`/events/updateEvent/${id}`, eventData);
export const deleteEvent = (id) => api.delete(`/events/deleteEvent/${id}`);
export const getEventParticipants=(id) => api.get(`/events/getEventParticipants/${id}`);
export const registerParticipant = (eventId, participantId) =>
    api.post(`/events/${eventId}/addParticipant/${participantId}`);
export const unregisterParticipant = (eventId, participantId) =>
    api.post(`/events/${eventId}/removeParticipant/${participantId}`);