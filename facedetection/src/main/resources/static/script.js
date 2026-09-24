document.addEventListener('DOMContentLoaded', () => {
    const imageInput = document.getElementById('imageInput');
    const dropZone = document.getElementById('dropZone');
    const dropZoneContent = document.getElementById('dropZoneContent');
    const previewContainer = document.getElementById('previewContainer');
    const uploadPreview = document.getElementById('uploadPreview');
    const removeFileBtn = document.getElementById('removeFileBtn');
    const submitBtn = document.getElementById('submitBtn');
    const uploadForm = document.getElementById('uploadForm');
    const btnSpinner = document.getElementById('btnSpinner');

    const resultsDashboard = document.getElementById('resultsDashboard');
    const facesCountSpan = document.getElementById('facesCount');
    const resultImage = document.getElementById('resultImage');

    // Drag & Drop handlers
    ['dragenter', 'dragover'].forEach(eventName => {
        dropZone.addEventListener(eventName, (e) => {
            e.preventDefault();
            dropZone.classList.add('drag-over');
        });
    });

    ['dragleave', 'drop'].forEach(eventName => {
        dropZone.addEventListener(eventName, (e) => {
            e.preventDefault();
            dropZone.classList.remove('drag-over');
        });
    });

    // Preview image lors de la sélection
    imageInput.addEventListener('change', handleFileSelect);

    function handleFileSelect() {
        const file = imageInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                uploadPreview.src = e.target.result;
                dropZoneContent.classList.add('hidden');
                previewContainer.classList.remove('hidden');
                submitBtn.disabled = false;
            };
            reader.readAsDataURL(file);
        }
    }

    // Supprimer l'image sélectionnée
    removeFileBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        imageInput.value = '';
        dropZoneContent.classList.remove('hidden');
        previewContainer.classList.add('hidden');
        submitBtn.disabled = true;
        resultsDashboard.classList.add('hidden');
    });

    // Envoi du formulaire au Backend Spring Boot
    uploadForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const file = imageInput.files[0];
        if (!file) return;

        const formData = new FormData();
        formData.append('image', file);

        // UI Feedback - En cours
        submitBtn.disabled = true;
        btnSpinner.classList.remove('hidden');

        try {
            const response = await fetch('/api/faces/detect', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error('Erreur lors de l\'analyse par le serveur.');
            }

            const data = await response.json();

            // Remplissage des résultats
            facesCountSpan.textContent = data.facesCount;
            resultImage.src = data.base64Image;

            // Afficher le dashboard
            resultsDashboard.classList.remove('hidden');
            resultsDashboard.scrollIntoView({ behavior: 'smooth' });

        } catch (error) {
            alert('Une erreur est survenue : ' + error.message);
        } finally {
            submitBtn.disabled = false;
            btnSpinner.classList.add('hidden');
        }
    });
});