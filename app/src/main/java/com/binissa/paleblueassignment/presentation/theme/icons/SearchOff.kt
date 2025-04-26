package com.binissa.paleblueassignment.presentation.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val SearchOff: ImageVector
	get() {
		if (_SearchOff != null) {
			return _SearchOff!!
		}
		_SearchOff = ImageVector.Builder(
            name = "Search_off",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
			path(
    			fill = SolidColor(Color.Black),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.NonZero
			) {
				moveTo(280f, 880f)
				quadToRelative(-83f, 0f, -141.5f, -58.5f)
				reflectiveQuadTo(80f, 680f)
				reflectiveQuadToRelative(58.5f, -141.5f)
				reflectiveQuadTo(280f, 480f)
				reflectiveQuadToRelative(141.5f, 58.5f)
				reflectiveQuadTo(480f, 680f)
				reflectiveQuadToRelative(-58.5f, 141.5f)
				reflectiveQuadTo(280f, 880f)
				moveToRelative(544f, -40f)
				lineTo(568f, 584f)
				quadToRelative(-12f, -13f, -25.5f, -26.5f)
				reflectiveQuadTo(516f, 532f)
				quadToRelative(38f, -24f, 61f, -64f)
				reflectiveQuadToRelative(23f, -88f)
				quadToRelative(0f, -75f, -52.5f, -127.5f)
				reflectiveQuadTo(420f, 200f)
				reflectiveQuadToRelative(-127.5f, 52.5f)
				reflectiveQuadTo(240f, 380f)
				quadToRelative(0f, 6f, 0.5f, 11.5f)
				reflectiveQuadTo(242f, 403f)
				quadToRelative(-18f, 2f, -39.5f, 8f)
				reflectiveQuadTo(164f, 425f)
				quadToRelative(-2f, -11f, -3f, -22f)
				reflectiveQuadToRelative(-1f, -23f)
				quadToRelative(0f, -109f, 75.5f, -184.5f)
				reflectiveQuadTo(420f, 120f)
				reflectiveQuadToRelative(184.5f, 75.5f)
				reflectiveQuadTo(680f, 380f)
				quadToRelative(0f, 43f, -13.5f, 81.5f)
				reflectiveQuadTo(629f, 532f)
				lineToRelative(251f, 252f)
				close()
				moveToRelative(-615f, -61f)
				lineToRelative(71f, -71f)
				lineToRelative(70f, 71f)
				lineToRelative(29f, -28f)
				lineToRelative(-71f, -71f)
				lineToRelative(71f, -71f)
				lineToRelative(-28f, -28f)
				lineToRelative(-71f, 71f)
				lineToRelative(-71f, -71f)
				lineToRelative(-28f, 28f)
				lineToRelative(71f, 71f)
				lineToRelative(-71f, 71f)
				close()
			}
		}.build()
		return _SearchOff!!
	}

private var _SearchOff: ImageVector? = null
