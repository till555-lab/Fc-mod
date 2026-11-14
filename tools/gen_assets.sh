#!/bin/bash
set -e
R=src/main/resources
T=$R/assets/femboycompanion/textures/item
M=$R/assets/femboycompanion/models/item
D=$R/data/femboycompanion/recipes
mkdir -p "$T" "$M" "$D"

dec() { echo "$2" | base64 -d > "$T/$1.png"; }
dec white_ears "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAnUlEQVR4nGNgwAMqanv+V9T2/MenBq/mT5++/v/06SteQ5jIMp0U2z99+vr/2q0nOF1B0AVM20+Tb/uXlQf+f1l5AK8rKA4DnAYgO51p+2kGGQlBhrjYCAZ0V6AYUFHb87+qNJNyF2ALOFyugBtAju1YXYAv2rC5gokS2xkYGBhY0AX+eZoS1ARzBQMDw39GmCA5ua6juYSRsCoCAABUKmo1UeH3AwAAAABJRU5ErkJggg=="
dec white_tail "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAjklEQVR4nGNkwAMqanv+V5VmYohff/CSgYGBgWH96nUMjORqhgEmSjRjNQCXZlwAxQBCmjUVxHEbQKzNyIasX72OgYU4hyJAW/d0OLujuYQRbvunT18J4oranv/oBhLlApitcBuRASHbsdmKDLCmA2SbsdqKDMi1Ga8BRGvGZgBJmhkYUAORZM3IhpCrGQBfjselJKIrpAAAAABJRU5ErkJggg=="
dec white_paws "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAoElEQVR4nKVTMQrDMAy8mH42ewMm4KkQAvWeN/QFXbP3NyGZ3cnGliXhJAcCyehOsoQAAdb5wPkUD4k8Dj0ABACI/vx6djS3o8SMUGF6LwCAXMgwVVWMQ19/yToftu1IFmPrfOHTnKIDqV3qi5AqSZ2pIpo1k/fPmkwTMZGcb8B8f4U4jfNNiENsxW2BhKszUEVObaFFhCNX1xVFuHfuGv/3CTyO4ubJSQAAAABJRU5ErkJggg=="
dec black_ears "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAiUlEQVR4nGNgwAO4uPj+c3Hx/cenhgmfZi0tPQYtLT0GfIbgNIBYgNUAmO0wICUlh9MVBF0QpmZCmguQbYdpFhAQwOkK2oQBsu0wNi5XoBiAHnhkuwBbwOFyBdwAcmzH6gJ80YbNFUyU2M7AwMDASK4BHz58YHj27BHEAJghpNr+7dsnRsKqCAAAqs0mclsa46YAAAAASUVORK5CYII="
dec black_tail "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAlUlEQVR4nKWSMQ7DIBAEB5TqCh5iS6nyXn+Ar6Sy5HuIC1pSOcEJxIe9JWh2TgeOPxEJeRjuP+fj+AAgxgl3Ft7ir8DVghbcyq7gCF6WZ7vAai5LYpy42Qb9RHVGdQYgpdW5HrvqTErr7uVME5TG7zt3ZK9Zy1T/gRUG8C27BW5OYIWrBT0wgN82fAZ+RyRkkZC7QeAFwedPrqUbKPEAAAAASUVORK5CYII="
dec black_paws "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAApUlEQVR4nK1RIRLDMAxTCwP6jAGDoL1lj+xbigoC9oyC0A7Z5zrK0rtNSO1ZlqwAHaS0nIxHzD2xSEZKy+k5m52Yq0imbqXsAIBaD9NZAnUaIaaZo1gkm1Mp+4X7GV1CO/BxI4+wWzSFd2JLdUZ7aEoc9eDFNIHi9XgaX99bd0lTYhSz71sl3sV/FtR6TP6p4s3fOvj5FS4nxCQjcZPAJ2H/oxgAPvZXbSGcs5abAAAAAElFTkSuQmCC"
dec white_femboy_spawn_egg "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAgklEQVR4nGNkwAEqanv+I/M7mksYcanFqvnTp68oGN1AkjQTbQg2zatOvP6/6sRrnIaw4DJsx7VvRLkYp+3oNuPyCk4XeGhxYbgGWQwGmEhzJybA6QJcriHKBTuufSM6EGnjBWxOxheQcIAvKmFs9IREtBc8tLjw247LFVTLULg0AwDCgeQCYvY/zwAAAABJRU5ErkJggg=="
dec black_femboy_spawn_egg "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAoklEQVR4nLWTIRLFIAxEl85XiB4kgkNg/plrOAQiB6nAUkWHwmY+XzQuDLu87AQHo7zfa9+Xcjp2jx56v1eR8DhTzdRkWxEDgEiYqCYDJk7pQEqHafJhIzThSt0GFnqM30cvEqCaa8vDJOiFjWY0A0iI/5ZJYNEsEfTJ/6p3RmDIVpA3QSmnU82TaBxlXOmlEC0qgHwma6HY6wAJkY1iiQHgAvBiUBV8lsuaAAAAAElFTkSuQmCC"

for n in white_femboy_spawn_egg black_femboy_spawn_egg white_ears white_tail white_paws black_ears black_tail black_paws; do
  echo "{\"parent\":\"item/generated\",\"textures\":{\"layer0\":\"femboycompanion:item/$n\"}}" > "$M/$n.json"
done

for c in white black; do
  echo "{\"type\":\"minecraft:crafting_shaped\",\"pattern\":[\"W L\",\" W \"],\"key\":{\"W\":{\"item\":\"minecraft:${c}_wool\"},\"L\":{\"item\":\"minecraft:lead\"}},\"result\":{\"item\":\"femboycompanion:${c}_tail\",\"count\":1}}" > "$D/${c}_tail.json"
  echo "{\"type\":\"minecraft:crafting_shaped\",\"pattern\":[\" W \",\"WPW\"],\"key\":{\"W\":{\"item\":\"minecraft:${c}_wool\"},\"P\":{\"item\":\"minecraft:pink_wool\"}},\"result\":{\"item\":\"femboycompanion:${c}_ears\",\"count\":1}}" > "$D/${c}_ears.json"
  echo "{\"type\":\"minecraft:crafting_shaped\",\"pattern\":[\" W \",\"WBW\"],\"key\":{\"W\":{\"item\":\"minecraft:${c}_wool\"},\"B\":{\"item\":\"minecraft:leather_boots\"}},\"result\":{\"item\":\"femboycompanion:${c}_paws\",\"count\":1}}" > "$D/${c}_paws.json"
done

python3 tools/gen_textures.py
