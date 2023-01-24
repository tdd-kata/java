// https://pqina.nl/filepond/docs/api/plugins/image-preview/
FilePond.registerPlugin(
    FilePondPluginImagePreview,
);

// https://pqina.nl/filepond/docs/
const pond = FilePond.create();

window.onload = async function () {

    pond.setOptions({
        maxFiles: 100,
        instantUpload: false, // drop 하자마자 자동 업로드되지 않도록
        allowProcess: true, // 업로드 버튼을 눌러야 업로드되도록
        maxParallelUploads: 3,
        multiple: true, // 한번에 여러 파일을 선택할 수 있도록
        name: 'multipleFiles',
        ignoredFiles: [
            'Thumbs.db',
            '.DS_Store',
            'desktop.ini',
        ],

        allowReorder: true,
        allowMultiple: true,
        chunkUploads: true,

        server: {
            // https://pqina.nl/filepond/docs/api/server/
            // url: 'http://localhost:8080',
            process: '/filepond',
            // load: `/fileload/${id}/`,
        },

        // image-preview
        // https://pqina.nl/filepond/docs/api/plugins/image-preview/
        // imagePreviewMinHeight: 150,
        // imagePreviewMaxHeight: 150,
        imagePreviewHeight: 150,

        // image-edit
        // https://pqina.nl/filepond/docs/api/plugins/image-edit/
        // image-resize
        // https://pqina.nl/filepond/docs/api/plugins/image-resize/

        oninit: () => {
            console.log("oninit");
        },
    });

    // FilePond.parse(document.body);
    document.getElementById('filepond').appendChild(pond.element);

}

function uploadFilePond() {
    // https://pqina.nl/filepond/docs/api/file-item/
    let uploadFiles = pond.getFiles();
    console.log("upload files...");
    for (const uploadFile of uploadFiles) {
        console.log(uploadFile.file.name);
    }

    // upload
    pond.processFiles()
        .then((successFiles) => {
            console.log("success files,,,");
            for (const successFile of successFiles) {
                console.log(successFile.file.name);
            }
        })
}
