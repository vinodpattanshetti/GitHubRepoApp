# GitHubRepoApp
1) In this App the api https://api.github.com/repos/{owner}/{repo}/pulls?state=open is called to get Open Pull Requests for Particular users Repository.

2) GitHubInputActivity in this activity page, we are asking User to put his Username and Password, once user puts his/her username and password we are calling above api which returns List of Open Pull Requests.

3)RecyclerViewListActivity in this activity we are simply displaying the list of open pull requests as a part page which has listing in RecyclerView list.
