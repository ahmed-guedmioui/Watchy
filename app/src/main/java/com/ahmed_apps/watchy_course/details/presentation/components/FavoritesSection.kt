package com.ahmed_apps.watchy_course.details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed_apps.watchy_course.R
import com.ahmed_apps.watchy_course.details.presentation.details.DetailsState
import com.ahmed_apps.watchy_course.details.presentation.details.DetailsUiEvents

/**
 * @author Ahmed Guedmioui
 */
@Composable
fun FavoritesSection(
    modifier: Modifier,
    detailsState: DetailsState,
    onEvent: (DetailsUiEvents) -> Unit
) {

    if (detailsState.showAlertDialog) {
        FavoritesAlertDialog(
            detailsState = detailsState,
            onEvent = onEvent
        )
    }

    detailsState.media?.let { media ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            FloatingActionButton(
                onClick = {
                    onEvent(
                        DetailsUiEvents.ShowOrHideAlertDialog(1)
                    )
                }
            ) {
                if (media.isLiked) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = stringResource(R.string.like)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(R.string.disLike)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            FloatingActionButton(
                modifier = Modifier.fillMaxWidth(1f),
                onClick = {
                    onEvent(
                        DetailsUiEvents.ShowOrHideAlertDialog(2)
                    )
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (media.isBookmarked) {
                        Icon(
                            imageVector = Icons.Rounded.Bookmarks,
                            contentDescription = stringResource(R.string.bookmark)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.BookmarkBorder,
                            contentDescription = stringResource(R.string.unbookmark)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = if (media.isBookmarked) {
                            stringResource(R.string.unbookmark)
                        } else {
                            stringResource(R.string.bookmark)
                        }
                    )
                }

            }

        }
    }
}

@Composable
fun FavoritesAlertDialog(
    detailsState: DetailsState,
    onEvent: (DetailsUiEvents) -> Unit
) {

    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = if (detailsState.alertDialogType == 1) {
                    stringResource(R.string.remove_from_favorites)
                } else {
                    stringResource(R.string.remote_from_bookmarks)
                },
                fontSize = 17.sp
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (detailsState.alertDialogType == 1) {
                        onEvent(DetailsUiEvents.LikeOrDislike)
                    } else {
                        onEvent(DetailsUiEvents.BookmarkOrUnBookmark)
                    }
                }
            ) {
                Text(text = stringResource(R.string.yes))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onEvent(DetailsUiEvents.ShowOrHideAlertDialog())
                }
            ) {
                Text(text = stringResource(R.string.no))
            }
        }
    )

}





















