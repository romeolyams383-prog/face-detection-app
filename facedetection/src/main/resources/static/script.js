document.getElementById('uploadForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const imageInput = document.getElementById('imageInput');
    const file = imageInput.files[0];

    if (!file) {
        alert('Veuillez sélectionner une image.');
        return;
    }

    const formData = new FormData();
    formData.append('image', file);

    const loadingDiv = document.getElementById('loading');
    const resultSection = document.getElementById('resultSection');
    const facesCountSpan = document.getElementById('facesCount');
    const resultImage = document.getElementById('resultImage');

    // Afficher le loader et masquer l'ancien résultat
    loadingDiv.classList.remove('hidden');
    resultSection.classList.add('hidden');

    try {
        const response = await fetch('/api/faces/detect', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            throw new Error('Erreur lors du traitement de l\'image.');
        }

        const data = await response.json();

        // Mettre à jour le DOM sans rechargement de page
        facesCountSpan.textContent = data.facesCount;
        resultImage.src = data.base64Image;

        resultSection.classList.remove('hidden');
    } catch (error) {
        alert('Une erreur est survenue : ' + error.message);
    } finally {
        loadingDiv.classList.add('hidden');
    }
});