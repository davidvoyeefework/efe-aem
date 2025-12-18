(() => {
  const YT_SRC = "https://www.youtube.com/iframe_api";

  function initPlannerBioVideo() {
    const playerContainer = document.getElementById("player-container");
    if (!playerContainer) return;

    const vidId = playerContainer.dataset.youtubeId;
    if (!vidId) return;

    let player;

    const onPlayerStateChange = (event) => {
      if (event.data === YT.PlayerState.ENDED) {
        player.stopVideo();
      }
    };

    const onPlayerReady = () => {
      const efeLogo = document.getElementById("efe-logo");
      const playButton = document.getElementById("custom-play-button");

      ["custom-play-button", "efe-logo"].forEach((id) => {
        const el = document.getElementById(id);
        if (el) {
          el.addEventListener(
            "click",
            () => {
              player.playVideo();
              if (efeLogo) efeLogo.style.display = "none";
              if (playButton) playButton.style.display = "none";
            },
            { once: true }
          );
        }
      });
    };

    const createPlayer = () => {
      if (player) return;
      if (!window.YT || !window.YT.Player) return;

      player = new YT.Player("player", {
        height: "100%",
        width: "100%",
        videoId: vidId,
        playerVars: {
          rel: 0,
          color: "white",
          modestbranding: 1
        },
        events: {
          onReady: onPlayerReady,
          onStateChange: onPlayerStateChange
        }
      });
    };

    if (window.YT && window.YT.Player) {
      createPlayer();
      return;
    }

    window.__efeYTReadyQueue = window.__efeYTReadyQueue || [];
    window.__efeYTReadyQueue.push(createPlayer);

    if (!window.__efeYTReadyHandlerSet) {
      window.__efeYTReadyHandlerSet = true;

      const prev = window.onYouTubeIframeAPIReady;
      window.onYouTubeIframeAPIReady = function () {
        if (typeof prev === "function") prev();

        const q = window.__efeYTReadyQueue || [];
        window.__efeYTReadyQueue = [];
        q.forEach((fn) => {
          try {
            fn();
          } catch (e) {
            console.error("YT init failed", e);
          }
        });
      };
    }

    if (!document.querySelector(`script[src="${YT_SRC}"]`)) {
      const tag = document.createElement("script");
      tag.src = YT_SRC;
      document.head.appendChild(tag);
    }
  }

  if (document.readyState === "loading") {
    window.addEventListener("DOMContentLoaded", initPlannerBioVideo);
  } else {
    initPlannerBioVideo();
  }
})();
