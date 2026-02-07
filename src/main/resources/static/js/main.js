document.addEventListener('DOMContentLoaded', () => {
    const input = document.querySelector('#terminal-input');

    input.addEventListener('keydown', (e) => {
        if (e.key === "Enter") {
            async function sendData(data) {
                const response = await fetch('/api/game/command', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data),
                });

                if (!response.ok) {
                    throw new Error(`Request failed: ${response.status}`);
                }

                response.json(); // or response.text() etc.
            }
        }
    });
});