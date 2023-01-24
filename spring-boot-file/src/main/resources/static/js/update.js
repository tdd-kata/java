// https://pqina.nl/filepond/docs/api/plugins/image-preview/
FilePond.registerPlugin(
    FilePondPluginImagePreview,
);

// https://pqina.nl/filepond/docs/
const pond = FilePond.create();

window.onload = async function () {

    // 배열 순서대로 표시한다.
    // https://github.com/pqina/filepond/issues/282#issuecomment-477603114
    // 혹은 메타데이터에 index를 넣어서 정렬(sort)한다.
    let initialFileNames = [
        // '6700 series.jpg',
        // '61.111.18.175_8080_main.png',
        // '7500ars (7513).jpg',
        // '7500ars (7513).jpg',
        // '7500ars (7513).jpg',
    ];

    const id = window.location.pathname.split("/").pop();
    await fetch(`/files/${id}`)
        .then(response => response.json())
        .then(data => {
            initialFileNames.push(...data);
        });

    let initialFiles = [];

    for (const initialFileName of initialFileNames) {
        initialFiles.push({
            // the server file reference
            source: initialFileName,

            // set type to local to indicate an already uploaded file
            options: {
                type: 'local',
            },
        });
    }

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

        // application/offset+octet-stream
        // https://pqina.nl/filepond/docs/api/server/#process-chunks
        // chunkUploads: true,

        // https://pqina.nl/filepond/docs/api/server/#revert
        allowRevert: false,
        allowRemove: true,

        server: {
            // https://pqina.nl/filepond/docs/api/server/
            // url: 'http://localhost:8080',
            process: `/filepond-append/${id}`,
            load: `/fileload/${id}/`,
            revert: null,
            remove: async (source, load, _) => {
                await fetch(`/filepond/${id}/${source}`, {
                    method: 'DELETE',
                });

                // Should call the load method when done, no parameters required
                load();
            },
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

        // https://pqina.nl/filepond/docs/api/instance/properties/#files
        files: initialFiles
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
