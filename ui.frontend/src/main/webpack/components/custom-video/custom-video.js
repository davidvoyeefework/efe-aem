export default class CustomVideo {
    constructor(el) {
        this.el = el;
        this.player;
        this.thumbnailContainerEl = el.querySelector(".thumbnail_container");
        this.thumbnailEl =
            this.thumbnailContainerEl.querySelector("#thumbnail");
        this.playButton = el.querySelector(".start-video");
        this.playerEl = el.querySelector("#player");
        this.playButton.addEventListener(
            "click",
            this.handleCustomPlayButton.bind(this)
        );
        this.thumbnailImage = el.dataset.thumbnailImage || '';
        this.videoId = el.dataset.videoId;
        this.initVideo();

        this.onIframeReady = this.onIframeReady.bind(this);
        this.onPlayerReady = this.onPlayerReady.bind(this);
        this.hideDefaults = this.hideDefaults.bind(this);
    }

    static init(el) {
        return new CustomVideo(el);
    }

    async loadYTIframeAPI() {
        const tag = document.createElement("script");
        tag.src = "https://www.youtube.com/iframe_api";

        const firstScriptTag = document.getElementsByTagName("script")[0];
        firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

        await new Promise((resolve) => {
            window.onYouTubeIframeAPIReady = () => {
                this.onIframeReady();
                resolve();
            };
        }).then(() => {
            this.hideDefaults();
        });
    }

    hideDefaults() {
        this.playerEl.style.display = "none";
        this.thumbnailEl.src = this.thumbnailImage;
    }

    onIframeReady() {
        this.player = new YT.Player("player", {
            width: "100%",
            height: "100%",
            videoId: this.videoId,
            playerVars: {
                playsinline: 1,
            },
            events: {
                onReady: this.onPlayerReady,
            },
        });

        this.playerEl = this.player.getIframe();
    }

    onPlayerReady(event) {
        event.target.playVideo();
    }

    initVideo() {
        this.loadYTIframeAPI(this.onIframeReady);
    }

    handleCustomPlayButton() {
        this.playButton.style.display = "none";
        this.playerEl.style.display = "block";
        this.thumbnailContainerEl.style.display = "none";
        this.player.playVideo();
    }
}
