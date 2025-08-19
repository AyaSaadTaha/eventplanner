import { useState, useEffect } from 'react';
import { getParticipants, deleteParticipant } from '../services/participantService';
import ParticipantList from '../components/ParticipantList';
import ParticipantForm from '../components/ParticipantForm';
import { Button, Box } from '@mui/material';

export default function ParticipantsPage() {
    const [participants, setParticipants] = useState([]);
    const [editingParticipant, setEditingParticipant] = useState(null);

    const fetchParticipants = async () => {
        try {
            const { data } = await getParticipants();
            setParticipants(data);
        } catch (error) {
            console.error('Failed to fetch participants:', error);
        }
    };

    useEffect(() => {
        fetchParticipants();
    }, []);

    const handleDelete = async (id) => {
        try {
            await deleteParticipant(id);
            fetchParticipants();
        } catch (error) {
            console.error('Failed to delete participant:', error);
        }
    };

    return (
        <Box sx={{ p: 3 }}>
            <h1>Participants Management</h1>
            <ParticipantForm
                participant={editingParticipant}
                onSuccess={() => {
                    setEditingParticipant(null);
                    fetchParticipants();
                }}
            />
            <ParticipantList
                participants={participants}
                onEdit={setEditingParticipant}
                onDelete={handleDelete}
            />
        </Box>
    );
}