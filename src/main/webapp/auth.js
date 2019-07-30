function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  var id_token = googleUser.getAuthResponse().id_token;
  const xhr = new XMLHttpRequest();
  const params = new URLSearchParams();
  params.append('id-token', id_token);
  xhr.addEventListener('load', onLoginResponse);
  xhr.open('POST', 'auth');
  xhr.send(params);
  $(".g-signin2").css("display", "none");
  $(".data").css("display", "block");
  $("#pic").css('src', profile.getImageUrl());
  $("#email").text(profile.getEmail());
  showContents(['google-content', 'schedules-content', 'tasks-content']);
}
