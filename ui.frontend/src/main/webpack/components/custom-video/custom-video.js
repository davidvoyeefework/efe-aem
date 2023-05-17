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
      const videoId = playerElement.dataset.videoId;
      const playerEl = playerElement.querySelector(".player");
      const playButton = playerElement.querySelector(".start-video");
      const thumbnailContainerEl = playerElement.querySelector(
        ".thumbnail_container"
      );
      const thumbnailUrl = playerElement.dataset.thumbnailImage;
      const thumbnailEl = playerElement.querySelector(".thumbnail");
      thumbnailEl.src = thumbnailUrl;

      const player = new YT.Player(playerEl, {
        videoId: videoId,
        playerVars: {
          playsInline: 1,
        },
        events: {
          onReady: onPlayerReady(playerEl, playButton, thumbnailContainerEl),
        },
      });

      players.push(player);
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
