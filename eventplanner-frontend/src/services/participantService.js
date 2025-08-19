import api from './api';

export const getParticipants = () => api.get('/participants/getAllParticipants');
export const getParticipantById = (id) => api.get(`/participants/getParticipantById/${id}`);
export const createParticipant = (participantData) => api.post('/participants', participantData);
export const updateParticipant = (id, participantData) => api.put(`/participants/updateParticipant/${id}`, participantData);
export const deleteParticipant = (id) => api.delete(`/participants/deleteParticipant/${id}`);
export const getEventParticipants=(id) => api.get(`/participants/getEventParticipants/${id}`);
export const registerParticipant = (participantId , eventId) =>
    api.post(`/participants/${participantId}/register/${eventId}`);
export const unregisterParticipant = (participantId , eventId) =>
    api.post(`/participants/${participantId}/unregister/${eventId}`);