# ActivityLauncher
ActivityLauncher armed at replacing startActivityForResult/onActivityResult APIs.

## Principles
* maintain an invisible router Fragment in Activity/Fragment. Invoke router fragment#startActivityForResult when Activity/Fragment#startActivityForResult. Then invoke relative callback in onActivityResult of router Fragment.

## Change logs
*  supported Activity.

## To-dos
*  support Fragment.
