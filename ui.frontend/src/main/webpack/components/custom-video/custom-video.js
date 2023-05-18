(function (window) {
  // Load the IFrame Player API code asynchronously
  const tag = document.createElement("script");
  tag.src = "https://www.youtube.com/iframe_api";
  const firstScriptTag = document.getElementsByTagName("script")[0];
  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

  let players = [];
  window.onYouTubeIframeAPIReady = function onYouTubeIframeAPIReady() {
    const playerElements = document.getElementsByClassName("cmp-custom-video");

    for (let i = 0; i < playerElements.length; i++) {
      const playerElement = playerElements[i];
      const videoId = playerElement.dataset?.videoId;
      const playerEl = playerElement.querySelector(".player");
      const playButton = playerElement.querySelector(".start-video");
      const thumbnailContainerEl = playerElement.querySelector(
        ".thumbnail_container"
      );
      const dataEfeLayer = JSON.parse(playerElement.dataset?.efeLayer);
      const thumbnailUrl = playerElement.dataset?.thumbnailImage;
      const thumbnailEl = playerElement.querySelector(".thumbnail");
      thumbnailEl.src = thumbnailUrl;

      const player = new YT.Player(playerEl, {
        videoId: videoId,
        playerVars: {
          playsInline: 1,
        },
        events: {
          onReady: onPlayerReady(playerEl, playButton, thumbnailContainerEl),
          onStateChange: onPlayerStateChange(dataEfeLayer),
        },
      });

      players.push(player);
    }

    function onPlayerStateChange(dataEfeLayer) {
      return function (event) {
        if (event?.data === YT?.PlayerState?.PLAYING) {
          const updatedEfeLayer = JSON.parse(JSON.stringify(dataEfeLayer));
          updatedEfeLayer.video.videoTimed.starts.value = 1;
          window?.adobeDataLayer?.push(updatedEfeLayer);
        }
        if (event?.data === YT?.PlayerState?.ENDED) {
          const updatedEfeLayer = JSON.parse(JSON.stringify(dataEfeLayer));
          updatedEfeLayer.video.videoTimed.completes.value = 1;
          window?.adobeDataLayer?.push(updatedEfeLayer)
        }
      }
    }

    function onPlayerReady(playerEl, playButton, thumbnailContainerEl) {
      return function (event) {
        var player = event.target;
        playerEl = player.getIframe();
        playerEl.style.display = "none";

        playButton.addEventListener("click", function () {
          playButton.style.display = "none";
          playerEl.style.display = "block";
          thumbnailContainerEl.style.display = "none";
          player.playVideo();
        });
      };
    }
  };
})(window);
