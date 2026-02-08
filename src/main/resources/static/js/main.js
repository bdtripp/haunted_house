document.addEventListener('DOMContentLoaded', () => {
    const input = document.querySelector('#terminal-input');

    input.addEventListener('keydown', async (e) => {
        if (e.key === 'Enter') {
            const response = await fetch('/api/game/command', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ "command": e.target.value }),
            });

            if (!response.ok) {
                throw new Error(`Request failed: ${response.status}`);
            }

            response.json(); // or response.text() etc.
        }
    });
});