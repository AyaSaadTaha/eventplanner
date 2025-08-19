import { useState, useEffect } from 'react';
import { TextField, Button, Box, Typography } from '@mui/material';
import { createParticipant, updateParticipant } from '../services/participantService';

export default function ParticipantForm({ participant, onSuccess }) {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: ''
    });

    useEffect(() => {
        if (participant) {
            setFormData({
                firstName: participant.firstName,
                lastName: participant.lastName,
                email: participant.email
            });
        }
    }, [participant]);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (participant) {
                await updateParticipant(participant.id, formData);
            } else {
                await createParticipant(formData);
            }
            onSuccess();
            setFormData({ firstName: '', lastName: '', email: '' });
        } catch (error) {
            console.error('Error saving participant:', error);
        }
    };

    return (
        <Box component="form" onSubmit={handleSubmit} sx={{ mb: 3 }}>
            <Typography variant="h6" gutterBottom>
                {participant ? 'Edit Participant' : 'Add New Participant'}
            </Typography>
            <TextField
                name="firstName"
                label="First Name"
                value={formData.firstName}
                onChange={handleChange}
                fullWidth
                margin="normal"
                required
            />
            <TextField
                name="lastName"
                label="Last Name"
                value={formData.lastName}
                onChange={handleChange}
                fullWidth
                margin="normal"
                required
            />
            <TextField
                name="email"
                label="Email"
                type="email"
                value={formData.email}
                onChange={handleChange}
                fullWidth
                margin="normal"
                required
            />
            <Button type="submit" variant="contained" sx={{ mt: 2 }}>
                {participant ? 'Update' : 'Save'}
            </Button>
            {participant && (
                <Button
                    variant="outlined"
                    sx={{ mt: 2, ml: 2 }}
                    onClick={() => onSuccess()}
                >
                    Cancel
                </Button>
            )}
        </Box>
    );
}