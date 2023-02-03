class Blur {
    #rect = {};
    #drag = false;
    #imageObj = null;
    #blurVal = 80; // 블러 정도
    #canvas;
    #stepList = null;
    #currentStep = -1;

    constructor(canvasId, imageSrc) {
        let canvas = document.getElementById(canvasId);
        if (!canvas) {
            throw Error(`Not exist canvas. id: ${canvasId}`)
        }

        this.#canvas = canvas;
        this.#stepList = [];
        let rect = this.#rect;

        let ctx = canvas.getContext('2d', {willReadFrequently: true})

        this.#imageObj = new Image();
        this.#imageObj.onload = () => {
            canvas.width = this.#imageObj.width;
            canvas.height = this.#imageObj.height;
            ctx.drawImage(this.#imageObj, 0, 0);
        }
        // this.#imageObj.crossOrigin = "Anonymous";
        this.#imageObj.src = imageSrc;
        this.#stepList[this.#currentStep] = imageSrc;

        ctx.drawImage(this.#imageObj, 0, 0);

        let dragStart = (e) => {
            rect.startX = e.pageX - e.target.offsetLeft;
            rect.startY = e.pageY - e.target.offsetTop;
            this.#drag = true;
        }

        let dragEnd = () => {
            this.#drag = false;
            let src = canvas.toDataURL();
            this.#stepList[++this.#currentStep] = src;
            this.#imageObj.src = src;
        }

        let drag = (e) => {
            if (!this.#drag) {
                return;
            }

            ctx.filter = `blur(${this.#blurVal}px)`;
            ctx.drawImage(this.#imageObj, 0, 0);

            let imgData;

            rect.w = (e.pageX - e.target.offsetLeft) - rect.startX;
            rect.h = (e.pageY - e.target.offsetTop) - rect.startY;
            if (rect.h !== 0 && rect.w !== 0) {
                imgData = ctx.getImageData(rect.startX, rect.startY, rect.w, rect.h);
            }

            // base 이미지로 초기화
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.filter = 'none';
            ctx.drawImage(this.#imageObj, 0, 0);

            // 블러 처리할 위치
            let rw = rect.w < 0 ? rect.startX + rect.w : rect.startX;
            let rh = rect.h < 0 ? rect.startY + rect.h : rect.startY;

            // 블러 처리 이미지 넣어줌
            if (imgData) {
                ctx.putImageData(imgData, rw, rh);
                imgData = null;
            }
        }

        let keyEvent = (e) => {
            // undo
            // if (e.keyCode === 90 && e.ctrlKey) { // ctrl + z
            if (e.key === 'z' && e.ctrlKey) { // ctrl + z
                if (this.#currentStep > -1) {
                    this.#imageObj.src = this.#stepList[--this.#currentStep];
                    ctx.drawImage(this.#imageObj, 0, 0);
                }
            }

            // redo
            // if (e.keyCode === 90 && e.shiftKey && e.ctrlKey) { // ctrl + shift + z
            if (e.key === 'Z' && e.ctrlKey) { // ctrl + shift + z
                if (this.#currentStep < this.#stepList.length - 1) {
                    this.#imageObj.src = this.#stepList[++this.#currentStep];
                    ctx.drawImage(this.#imageObj, 0, 0);
                }
            }
        }

        canvas.addEventListener('mousedown', dragStart, false);
        canvas.addEventListener('mouseup', dragEnd, false);
        canvas.addEventListener('mousemove', drag, false);
        window.addEventListener('keydown', keyEvent, false);
    }

    downloadImage = () => {
        let link = document.createElement('a');
        link.href = this.#canvas.toDataURL();
        link.download = 'blurred'
        link.click();
    }
}
