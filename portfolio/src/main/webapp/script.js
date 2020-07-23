// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

"use strict";

const getCommentsURL = (limit) => `/comments?limit=${limit}`;
const deleteAllCommentsURL = '/delete-comments';

const STATUS_OK = 200;

function getCommentLimit() {
  const selector = document.getElementById('commentLimitSelect');
  return selector.options[selector.selectedIndex].value;
}

/**
 * Fetches comments stored in backend servlet.
 */
function getComments() {
  const limit = getCommentLimit();

  fetch(getCommentsURL(limit))
    .then(response => response.json())
    .then(comments => {
      const section = document.getElementById('commentSection');

      // Clear old comments to insert new comments.
      section.innerHTML = '';
      
      comments.forEach(comment => {
        const post = document.createElement('p');
        post.innerText = comment.comment;

        const author = document.createElement('p');
        author.innerText = 'Posted by ' + comment.name;

        section.appendChild(post);
        section.appendChild(author);
        section.appendChild(document.createElement('br'));
      })
    });
}

/**
 * Deletes all comments from backend and refreshes comments.
 */
function deleteAllComments() {
  const options = {
    method: 'POST'
  }

  fetch(deleteAllCommentsURL, options)
    .then(response => {
      if (response.status === STATUS_OK) {
        getComments();
      }
    });
}

/**
 * Opens a modal and configures close button.
 */
function openModal(modalId, closeId) {
  const modal = document.getElementById(modalId);
  modal.style.display = 'block';

  const closeBtn = document.getElementById(closeId);
  closeBtn.onclick = function() {
    modal.style.display = 'none';
  };
}

/**
 * Opens a modal with the given image and caption.
 */
function openSimpleImageModal(modalId, modalImgId, captionId, closeId, imgSrc, captionText) {  
  openModal(modalId, closeId);

  const modalImg = document.getElementById(modalImgId);
  const caption = document.getElementById(captionId);

  modalImg.src = imgSrc;
  caption.innerText = captionText;
}

/**
 * Create gallery elements and set images.
 */
function setGalleryImages() {
  const slideContainer = document.getElementsByClassName('slides-container')[0];
  const nextBtn = document.getElementsByClassName('next-slide')[0];
  
  // If we are on Home page gallery will not be defined.
  if (typeof slideContainer === 'undefined') {
    return;
  }

  for (const pic of picData) {
    const slidePic = document.createElement('div');
    slidePic.className = 'slide-pic';

    const counter = document.createElement('div');
    counter.className = 'slide-counter';

    const img = document.createElement('img');
    img.src = pic.imgSrc;

    slidePic.appendChild(counter);
    slidePic.appendChild(img);

    slideContainer.insertBefore(slidePic, nextBtn);
  }
}

/**
 * Advance or Reverse the slide currently showing.
 */
function advanceSlides(n) {
  currIndex += n;
  currIndex = showSlidePicture(currIndex);
  initMap();
};

/**
 * Show the image from the gallery at the given position.
 */
function showSlidePicture(n) {
  const slides = document.getElementsByClassName('slide-pic');
  const caption = document.getElementById('slide-caption');

  // If not on pics.html document wil not contain any slide elements.
  if (slides.length === 0) {
    return n;
  }

  const newIndex = circularIndex(n, slides);
  
  for (const slide of slides) {
    slide.style.display = 'none';
  }

  slides[newIndex].style.display = 'block';
  
  caption.innerText = picData[newIndex].caption;
  slides[newIndex].getElementsByClassName('slide-counter')[0].innerText =
     (newIndex + 1).toString() + '/' + slides.length.toString();

  return newIndex;
};

/**
 * Return correct index for a circular array.
 */
function circularIndex(n, items) {
  while (n < 0) {
    n += items.length;
  }

  return n % items.length;
}

/**
 * Styles main like button to the current reaction styling.
 */
function setReactBtn(reactBtn, iconClass, text, colour) {
  reactBtn.innerHTML = '';
  reactBtn.style.color = colour;

  const reactIcon = document.createElement('i');
  reactIcon.className = iconClass;

  const reactText = document.createElement('div');
  reactText.innerText = text;

  reactBtn.appendChild(reactIcon);
  reactBtn.appendChild(reactText)
}

/**
 * Flip only the selected reaction's icon to be solid colour.
 * @param reaction - currently selected reaction from react options.
 */
function highlightOption(reaction) {
  const options = document.getElementsByClassName('react-options')[0];
  const optionIcons = options.getElementsByClassName('fas');

  for (let icon of optionIcons) {
    icon.className = icon.className.replace('fas', 'far');
  }

  let highlightIcon;

  switch (reaction) {
    case reacts.like:
      highlightIcon = document.getElementById('likeBtn');
      break;
    case reacts.love:
      highlightIcon = document.getElementById('loveBtn');
      break;
    case reacts.wow:
      highlightIcon = document.getElementById('wowBtn');
      break;
    case reacts.laugh:
      highlightIcon = document.getElementById('laughBtn');
      break;
    default:
      return;
  }

  highlightIcon.className = reaction.solidIcon;
}

/**
 * Toggle reaction of clicked option, reverting to none if already clicked.
 * @param {string} clickedReact - option clicked by user.
 */
function toggleReaction(clickedReact) {
  const reactBtn = document.getElementsByClassName('user-react')[0];
  
  let reaction;
  switch (clickedReact) {
    case 'like':
      reaction = reacts.like;
      break;
    case 'love':
      reaction = reacts.love;
      break;
    case 'wow':
      reaction = reacts.wow;
      break;
    case 'laugh':
      reaction = reacts.laugh;
      break;
    case 'none':
      reaction = reacts.none;
      break;
    default:
      reaction = reacts.like;
      break;
  }

  // If a reaction is clicked again, it will unlike the post.
  reaction = (currReaction === reaction) ? reacts.none : reaction;

  setReactBtn(reactBtn, reaction.solidIcon, reaction.text, reaction.colour);
  highlightOption(reaction);
  currReaction = reaction;
}

/**
 * Handle logic of main like button to remove existing like when clicked,
 * but default to like on a click with no previous reaction.
 */
function toggleLike() {
  if (currReaction === reacts.none) {
    toggleReaction('like');
  } else {
    toggleReaction('none');
  }
}

/**
 * Initialise map for currently displayed image.
 */
function initMap() {
  const map = new google.maps.Map(
    document.getElementById('map'),
    {zoom: picData[currIndex].mapZoom, center: picData[currIndex].location}
  )

  const marker = new google.maps.Marker(
    {position: picData[currIndex].location, map: map}
  );
}

const picData = [
  {
    imgSrc: './images/gallery_1.jpeg',
    caption: 'This was taken outside King\'s College Chapel in Cambridge ' +
      'last summer. I decided to take a scenic route and accidentally timed ' +
      'this photo with a cyclist going past.',
    location: {lat: 52.204, lng: 0.117},
    mapZoom: 12
  },
  {
    imgSrc: './images/gallery_2.jpeg',
    caption: 'IC A vs Birmingham B in the NHSF National Kabaddi Tournament ' +
      'earlier this year. I joined Kabaddi at Imperial this year and I am ' +
      'loving it!',
    location: {lat: 52.546, lng: -2.052},
    mapZoom: 12
  },
  {
    imgSrc: './images/gallery_3.jpeg',
    caption: 'A photo of the Marina Bay Sands Hotel in Singapore during one ' +
      'of its light shows, taken from across the bay.',
    location: {lat: 1.285, lng: 103.854},
    mapZoom: 12
  },
  {
    imgSrc: './images/gallery_4.jpeg',
    caption: 'A firebreather in the desert. Taken during a desert safari ' +
      'and dinner excursion when visiting Dubai.',
    location: {lat: 25.179, lng: 55.299},
    mapZoom: 10
  },
  {
    imgSrc: './images/gallery_5.jpeg',
    caption: 'A close-up of a flower in the Flower Dome of the Gardens By ' +
      'the Bay in Singapore.',
    location: {lat: 1.284, lng: 103.865},
    mapZoom: 12
  }
];

const reacts = {
  none: {
    solidIcon: 'far fa-thumbs-up',
    outlineIcon: 'far fa-thumbs-up',
    text: 'Like',
    colour: '#9e9e9e'
  },
  like: {
    solidIcon: 'fas fa-thumbs-up',
    outlineIcon: 'far fa-thumbs-up',
    text: 'Like',
    colour: '#0fa3b1'
  },
  love: {
    solidIcon: 'fas fa-heart',
    outlineIcon: 'far fa-heart',
    text: 'Love',
    colour: '#ef5350'
  },
  wow: {
    solidIcon: 'fas fa-surprise',
    outlineIcon: 'far fa-surprise',
    text: 'Wow',
    colour: '#ffc107'
  },
  laugh: {
    solidIcon: 'fas fa-laugh-squint',
    outlineIcon: 'far fa-laugh-squint',
    text: 'Haha',
    colour: '#ffc107'
  }
}

setGalleryImages();
let currIndex = showSlidePicture(0);
let currReaction = reacts.none;