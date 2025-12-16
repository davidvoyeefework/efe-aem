// planner-bio.js
window.onload = () => {

    // Get the player container
    const playerContainer = document.getElementById("player-container");

    if (playerContainer) {
        const vidId = playerContainer.dataset.youtubeId;

        if (vidId) {
            // Load YouTube iframe API
            const tag = document.createElement('script');
            tag.src = "https://www.youtube.com/iframe_api";
            const firstScriptTag = document.getElementsByTagName('script')[0];
            firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

            let player;
            let hasPlayedOnce = false;

            // YouTube API ready callback
            window.onYouTubeIframeAPIReady = () => {
                player = new YT.Player('player', {
                    height: '100%',
                    width: '100%',
                    videoId: vidId,
                    playerVars: {
                        rel: 0,
                        color: 'white',
                        modestbranding: 1
                    },
                    events: {
                        'onReady': onPlayerReady,
                        'onStateChange': onPlayerStateChange
                    }
                });
            };

            // Stop video when it ends
            const onPlayerStateChange = (event) => {
                if (event.data === YT.PlayerState.ENDED) {
                    player.stopVideo();
                }
            };

            // Custom play button logic
            const onPlayerReady = (event) => {
                const efeLogo = document.getElementById("efe-logo");
                const playButton = document.getElementById("custom-play-button");

                ['custom-play-button', 'efe-logo'].forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.addEventListener('click', function () {
                            player.playVideo();
                            if (efeLogo) efeLogo.style.display = 'none';
                            if (playButton) playButton.style.display = 'none';
                        });
                    }
                });
            };
        }
    }
};
